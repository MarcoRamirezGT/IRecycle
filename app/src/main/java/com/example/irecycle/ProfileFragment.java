package com.example.irecycle;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.irecycle.databinding.FragmentProfileBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    AppPreferenceManager appPreferenceManager;

    String DISPLAY_NAME = null;
    int TAKE_IMAGE_CODE = 10001;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    EditText editTextUserName;
    TextView textUsername;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appPreferenceManager = new AppPreferenceManager(requireContext());

        setHasOptionsMenu(true);
    }

    // CHECK IF NIGHT MODE IS ACTIVATED
    public void checkNightModeActivated() {
        if (appPreferenceManager.getDarkModeState()) {
            binding.switchTheme.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            binding.switchTheme.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void saveNightModeState(boolean nighMode) {
        appPreferenceManager.setDarkModeState(nighMode);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        checkNightModeActivated();

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        textUsername = binding.textViewName;

        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();

        if (mUser != null) {
            if (mUser.getDisplayName() != null) {
                textUsername.setText(mUser.getDisplayName());
            }

            binding.textViewEmail.setText(mUser.getEmail());

            Glide.with(this)
                    .load(mUser.getPhotoUrl())
                    .centerCrop()
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .fallback(R.drawable.profile)
                    .into(binding.imageProfile);

            if (mUser.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(mUser.getPhotoUrl())
                        .centerCrop()
                        .placeholder(R.drawable.profile)
                        .error(R.drawable.profile)
                        .fallback(R.drawable.profile)
                        .into(binding.imageProfile);

            }
        }

        // GO TO CHANGE AVATAR DIALOG
        binding.layoutProfile.setOnClickListener(view -> showChangeAvatarDialog());

        // GO TO CHANGE NAME DIALOG
        binding.layoutName.setOnClickListener(view -> showChangeNameDialog());

        // CHANGE DARK THEME CODE
        binding.switchTheme.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                saveNightModeState(true);
                requireActivity().recreate();

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                saveNightModeState(false);
                requireActivity().recreate();
            }
        });


        // LOGOUT BUTTON CLICK
        binding.buttonLogout.setOnClickListener(view -> new MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                .setTitle(getResources().getString(R.string.sign_out_text))
                .setMessage(getResources().getString(R.string.sign_out_msg_text))
                .setPositiveButton(getResources().getString(R.string.yes_text), (dialogInterface, i) -> {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                })
                .setNegativeButton(getResources().getString(R.string.cancel_text), (dialogInterface, i) -> {
                    // DISPLAY A TOAST OR SNACK BAR
                })
                .show());
    }

    private void showChangeAvatarDialog() {

        AlertDialog.Builder switchViewDialog = new AlertDialog.Builder(getContext());

        final LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.dialog_avatar, null);

        switchViewDialog.setView(view);

        final AlertDialog dialog = switchViewDialog.create();

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        dialog.show();

        // CLICK ON LAYOUT TAKE PHOTO
        view.findViewById(R.id.layoutTakePhoto).setOnClickListener(view1 -> {
            // Hide dialog
            dialog.dismiss();

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivityForResult(intent, TAKE_IMAGE_CODE);
            }
        });

        // CLICK ON LAYOUT CHOOSE PHOTO
        view.findViewById(R.id.layoutChoosePhoto).setOnClickListener(view12 -> {
            // Hide dialog
            dialog.dismiss();

            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                // Permission not granted, request it
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                // Show popup for runtime permission
                requestPermissions(permissions, PERMISSION_CODE);

            } else {
                // Permission already granted
                pickImageFromGallery();
            }
        });
    }

    private void pickImageFromGallery() {
        // Intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    // Handle result for runtime permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImageFromGallery();
            } else {
                // Permission was denied
                Toast.makeText(getContext(), "Permission denied...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // Result for taking picture
        if (requestCode == TAKE_IMAGE_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = null;
                if (data != null) {
                    bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                }
                binding.imageProfile.setImageBitmap(bitmap);
                if (bitmap != null) {
                    handleUpload(bitmap);
                }
            }
        }

        // Result for picking image from gallery
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            // Set image to image view
            if (data != null) {
                binding.imageProfile.setImageURI(data.getData());
                setUserProfileUrl(data.getData());
            }
        }
    }

    private void handleUpload(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(uid + ".jpeg");

        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(taskSnapshot -> getDownloadUrl(reference))
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to get image", Toast.LENGTH_SHORT).show());
    }

    private void getDownloadUrl(StorageReference reference) {
        reference.getDownloadUrl()
                .addOnSuccessListener(this::setUserProfileUrl);
    }

    private void setUserProfileUrl(Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        if (user != null) {
            user.updateProfile(request)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "UPdated ", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to upload image", Toast.LENGTH_SHORT).show());
        }
    }

    private void showChangeNameDialog() {
        AlertDialog.Builder switchViewDialog = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.dialog_change_name, null);

        switchViewDialog.setView(view);

        final AlertDialog dialog = switchViewDialog.create();

        dialog.setCancelable(true);
        dialog.show();

        editTextUserName = view.findViewById(R.id.editTextUserName);

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            if (currentUser.getDisplayName() != null) {
                editTextUserName.setText(currentUser.getDisplayName());
                editTextUserName.setSelection(currentUser.getDisplayName().length());
            }
        }

        // CANCEL AND CLOSE DIALOG
        view.findViewById(R.id.buttonCancel).setOnClickListener(view1 -> dialog.dismiss());

        // GO TO UPDATE PROFILE FUNCTION
        view.findViewById(R.id.buttonOk).setOnClickListener(view12 -> {
            updateProfile(view12);
            dialog.dismiss();
        });
    }

    public void updateProfile(final View view) {

        view.setEnabled(false);

        DISPLAY_NAME = editTextUserName.getText().toString();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(DISPLAY_NAME)
                .build();

        mUser.updateProfile(request)
                .addOnSuccessListener(aVoid -> {
                    view.setEnabled(true);
                    textUsername.setText(mUser.getDisplayName());
                    Toast.makeText(getContext(), getString(R.string.username_updated_text), Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    view.setEnabled(true);
                    Toast.makeText(getContext(), getString(R.string.unable_to_update_username), Toast.LENGTH_SHORT).show();
                });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
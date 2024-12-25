# Student App

This application is used to display a list of 10 students retrieved from Firestore. It uses Firebase for authentication and storing student data.

## Features

- Displays a list of students with their names, addresses, and profile pictures.
- Allows searching for students by name using a search feature.
- Automatically adds student data to Firestore using seeder.
- Shows skeleton effect while data is loading.

## Project Structure

- **FirebaseInstance**: Singleton class to access Firebase Authentication and Firestore.
- **GlideImageLoader**: Utility class to load images using Glide.
- **StudentListActivity**: Main activity that displays the student list and allows searching.
- **StudentListAdapter**: Adapter for the RecyclerView to display student data.
- **StudentListViewHolder**: ViewHolder to display each student item in the RecyclerView.
- **StudentPlaceholderAdapter**: Adapter to show placeholder items while data is loading.
- **Student**: Data model for students containing id, name, address, and profile picture.
- **LoginActivity**: An activity that used to perform authentication using username and password.
- **BaseAuthActivity**: Basic authentication activity to make sure user is logged in before accessing the **StudentListActivity**.

## Dependencies
- Firebase Firestore
- Firebase Auth
- Glide
- Java Faker
- Shimmer

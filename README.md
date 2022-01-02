# ReadNote

ReadNote is an android app to allow users to create accounts, make lists of books and keep a list of notes for each book. This was developed for the module Mobile
Application Development in the WIT HDip in Computer Science 2020. 

The app requires Firebase Email/Password Authentication and Firebase Realtime Database setup with the following database rules:

```
{
  "rules": {
    "user-books": {
      "$uid": {
        ".write": "$uid === auth.uid",
        ".read": "$uid === auth.uid"
      }
    },
    "user-notes": {
      "$uid": {
        ".write": "$uid === auth.uid",
        ".read": "$uid === auth.uid"
      }
    }
  }
```

# NoteTakingApp
In this app,I signup &amp; login using firebase .
here features like reset password,update password, update email,update and set profile.
# Dependencies
dependency use for circle profile pic:
dependencies {
    ...
    implementation 'de.hdodenhof:circleimageview:3.1.0'
}
Add to xml file:-
<de.hdodenhof.circleimageview.CircleImageView
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/profile_image"
    android:layout_width="96dp"
    android:layout_height="96dp"
    android:src="@drawable/profile"
    app:civ_border_width="2dp"
    app:civ_border_color="#FF000000"/>
    https://github.com/hdodenhof/CircleImageView
 #
 -Animations dependency using in this project:-
 -implementation "androidx.dynamicanimation:dynamicanimation:1.0.0"
 -https://developer.android.com/guide/topics/graphics/spring-animation
 #
 -glide dependency use in this project:-
 -implementation 'com.github.bumptech.glide:glide:4.11.0'
 #
 -Firebase dependency using:-
  -implementation 'com.google.firebase:firebase-auth:19.3.1'
  -implementation 'com.google.firebase:firebase-database:19.3.0'
  -implementation 'com.google.firebase:firebase-storage:19.1.1'
  #
   add your project build.gradle(module app)
  
 

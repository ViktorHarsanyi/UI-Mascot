# UI-Mascot

### Light weight Android library that converts a TextView into an Office Manager like on Win XP

UI-Mascot is based on a state machine and ObjectAnimator.

There are 3 states available:

| Tag        | Random           | Idle  |
| ------------- |-------------| -----|
| Reads view tags for hints    | Moves around the screen randomly | Moves back and forth a specific view|

#### Usage:

  Add jitpack to your root.gradle file
~~~ gradle
allprojects {
    repositories {
      ...
        maven { url 'https://jitpack.io' }
    }
}
~~~

  Add dependency
~~~ gradle
	dependencies {
	        implementation 'com.github.ViktorHarsanyi:UI-Mascot:v1.0'
	}
~~~

  Initialize the state machine using the builder and add the screen width in pixels and the view you want to move.
~~~ java 

  DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        TextView mascotTextView = findViewById(R.id.mascot);
        
    MascotStateMachine mascot = new MascotStateMachine.Builder(mascotTextView,width)
                .setInterpolator(new OvershootInterpolator())
                .setDuration(2000L)
                .setAnimationType(MascotStateMachine.ROTATE_CW)
                .setIdleDestinationView(findViewById(R.id.textView))
                .setMirrored(true)
                .build();
~~~
  Set state and run.
~~~ java 

mascot.setState(MascotStateMachine.RANDOM_MODE);
mascot.run();
~~~

  Dont forget to dispose.
~~~ java 

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mascot.dispose();
    }
~~~

  Override methods
~~~ java 

mascot.move(view);
mascot.move(x,y);
mascot.talk("");
mascot.animate();
~~~

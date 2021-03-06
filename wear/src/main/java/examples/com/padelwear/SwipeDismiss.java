package examples.com.padelwear;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.SwipeDismissFrameLayout;

public class SwipeDismiss extends Activity{
	@Override protected void onCreate(Bundle savedIntanceState){
		super.onCreate(savedIntanceState);
		setContentView(R.layout.swipe_dismiss);
		SwipeDismissFrameLayout root =
				(SwipeDismissFrameLayout) findViewById(R.id.swipe_dismiss_root);
		root.addCallback(new SwipeDismissFrameLayout.Callback() {
			@Override public void onDismissed(SwipeDismissFrameLayout layout) {
				SwipeDismiss.this.finish();
			}
		});
	}
}

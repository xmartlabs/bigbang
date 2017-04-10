package com.xmartlabs.base.ui.common;

/**
 * Describes a progress dialog.
 * The UI and interactions are entirely up to the developer, providing it respects the following method definitions.
 */
public interface BaseProgressDialog {
  /** Makes the progress dialog visible to the user. **/
  void show();
  /** Hides the progress dialog, so it's no longer visible by the user. */
  void hide();
  /**
   * Destroys the dialog and releases memory.
   * It should be put into hidden state first, if visible.
   * The {@link #show()} method will yield no effect after calling this method.
   */
  void dismiss();
}

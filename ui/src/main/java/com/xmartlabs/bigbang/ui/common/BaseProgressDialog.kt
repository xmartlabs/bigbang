package com.xmartlabs.bigbang.ui.common

/**
 * Describes a progress dialog.
 * The UI and interactions are entirely up to the developer, providing it respects the following method definitions.
 */
interface BaseProgressDialog {
  /** Makes the progress dialog visible to the user.  */
  fun show()

  /** Hides the progress dialog, so it's no longer visible by the user.  */
  fun hide()

  /**
   * Destroys the dialog and releases memory.
   * It should be put into hidden state first, if visible.
   * The [.show] method will yield no effect after calling this method.
   */
  fun dismiss()
}

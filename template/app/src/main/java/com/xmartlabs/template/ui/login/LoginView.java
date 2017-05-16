package com.xmartlabs.template.ui.login;

import com.xmartlabs.template.ui.common.TemplateView;

public interface LoginView extends TemplateView {
  void setIsLoading(boolean loading);
  void gotoRecyclerExampleActivity();
}

package com.xmartlabs.template

import com.xmartlabs.template.module.AndroidModule
import com.xmartlabs.template.module.MockRestServiceModule

class TestApplication : BaseProjectApplication() {
  override fun createComponent() = DaggerTestComponent.builder()
      .coreAndroidModule(AndroidModule(this))
      .restServiceModule(MockRestServiceModule())
      .build()

  override fun createBullet(component: ApplicationComponent) = BulletTestComponent(component as TestComponent)
}

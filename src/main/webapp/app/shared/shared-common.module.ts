import { NgModule } from '@angular/core';

import { FixedAssetsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [FixedAssetsSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [FixedAssetsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class FixedAssetsSharedCommonModule {}

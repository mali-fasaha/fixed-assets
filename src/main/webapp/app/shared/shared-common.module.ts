import { NgModule } from '@angular/core';

import { FixedAssetsSharedLibsModule, GhaAlertComponent, GhaAlertErrorComponent } from './';

@NgModule({
  imports: [FixedAssetsSharedLibsModule],
  declarations: [GhaAlertComponent, GhaAlertErrorComponent],
  exports: [FixedAssetsSharedLibsModule, GhaAlertComponent, GhaAlertErrorComponent]
})
export class FixedAssetsSharedCommonModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FixedAssetsSharedCommonModule, GhaLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [FixedAssetsSharedCommonModule],
  declarations: [GhaLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [GhaLoginModalComponent],
  exports: [FixedAssetsSharedCommonModule, GhaLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsSharedModule {
  static forRoot() {
    return {
      ngModule: FixedAssetsSharedModule
    };
  }
}

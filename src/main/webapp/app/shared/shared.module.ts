import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FixedAssetsSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [FixedAssetsSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [FixedAssetsSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsSharedModule {
  static forRoot() {
    return {
      ngModule: FixedAssetsSharedModule
    };
  }
}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FixedAssetsSharedModule } from 'app/shared';
import {
  FixedAssetItemComponent,
  FixedAssetItemDetailComponent,
  FixedAssetItemUpdateComponent,
  FixedAssetItemDeletePopupComponent,
  FixedAssetItemDeleteDialogComponent,
  fixedAssetItemRoute,
  fixedAssetItemPopupRoute
} from './';

const ENTITY_STATES = [...fixedAssetItemRoute, ...fixedAssetItemPopupRoute];

@NgModule({
  imports: [FixedAssetsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FixedAssetItemComponent,
    FixedAssetItemDetailComponent,
    FixedAssetItemUpdateComponent,
    FixedAssetItemDeleteDialogComponent,
    FixedAssetItemDeletePopupComponent
  ],
  entryComponents: [
    FixedAssetItemComponent,
    FixedAssetItemUpdateComponent,
    FixedAssetItemDeleteDialogComponent,
    FixedAssetItemDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsFixedAssetItemModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FixedAssetsSharedModule } from 'app/shared';
import {
  FixedAssetCategoryComponent,
  FixedAssetCategoryDetailComponent,
  FixedAssetCategoryUpdateComponent,
  FixedAssetCategoryDeletePopupComponent,
  FixedAssetCategoryDeleteDialogComponent,
  fixedAssetCategoryRoute,
  fixedAssetCategoryPopupRoute
} from './';

const ENTITY_STATES = [...fixedAssetCategoryRoute, ...fixedAssetCategoryPopupRoute];

@NgModule({
  imports: [FixedAssetsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FixedAssetCategoryComponent,
    FixedAssetCategoryDetailComponent,
    FixedAssetCategoryUpdateComponent,
    FixedAssetCategoryDeleteDialogComponent,
    FixedAssetCategoryDeletePopupComponent
  ],
  entryComponents: [
    FixedAssetCategoryComponent,
    FixedAssetCategoryUpdateComponent,
    FixedAssetCategoryDeleteDialogComponent,
    FixedAssetCategoryDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsFixedAssetCategoryModule {}

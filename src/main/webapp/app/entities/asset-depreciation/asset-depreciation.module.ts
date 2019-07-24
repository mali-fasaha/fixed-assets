import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FixedAssetsSharedModule } from 'app/shared';
import {
  AssetDepreciationComponent,
  AssetDepreciationDetailComponent,
  AssetDepreciationUpdateComponent,
  AssetDepreciationDeletePopupComponent,
  AssetDepreciationDeleteDialogComponent,
  assetDepreciationRoute,
  assetDepreciationPopupRoute
} from './';

const ENTITY_STATES = [...assetDepreciationRoute, ...assetDepreciationPopupRoute];

@NgModule({
  imports: [FixedAssetsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AssetDepreciationComponent,
    AssetDepreciationDetailComponent,
    AssetDepreciationUpdateComponent,
    AssetDepreciationDeleteDialogComponent,
    AssetDepreciationDeletePopupComponent
  ],
  entryComponents: [
    AssetDepreciationComponent,
    AssetDepreciationUpdateComponent,
    AssetDepreciationDeleteDialogComponent,
    AssetDepreciationDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsAssetDepreciationModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FixedAssetsSharedModule } from 'app/shared';
import {
  AssetAcquisitionComponent,
  AssetAcquisitionDetailComponent,
  AssetAcquisitionUpdateComponent,
  AssetAcquisitionDeletePopupComponent,
  AssetAcquisitionDeleteDialogComponent,
  assetAcquisitionRoute,
  assetAcquisitionPopupRoute
} from './';

const ENTITY_STATES = [...assetAcquisitionRoute, ...assetAcquisitionPopupRoute];

@NgModule({
  imports: [FixedAssetsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AssetAcquisitionComponent,
    AssetAcquisitionDetailComponent,
    AssetAcquisitionUpdateComponent,
    AssetAcquisitionDeleteDialogComponent,
    AssetAcquisitionDeletePopupComponent
  ],
  entryComponents: [
    AssetAcquisitionComponent,
    AssetAcquisitionUpdateComponent,
    AssetAcquisitionDeleteDialogComponent,
    AssetAcquisitionDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsAssetAcquisitionModule {}

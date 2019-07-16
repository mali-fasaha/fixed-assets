import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FixedAssetsSharedModule } from 'app/shared';
import {
  AssetTransactionComponent,
  AssetTransactionDetailComponent,
  AssetTransactionUpdateComponent,
  AssetTransactionDeletePopupComponent,
  AssetTransactionDeleteDialogComponent,
  assetTransactionRoute,
  assetTransactionPopupRoute
} from './';

const ENTITY_STATES = [...assetTransactionRoute, ...assetTransactionPopupRoute];

@NgModule({
  imports: [FixedAssetsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AssetTransactionComponent,
    AssetTransactionDetailComponent,
    AssetTransactionUpdateComponent,
    AssetTransactionDeleteDialogComponent,
    AssetTransactionDeletePopupComponent
  ],
  entryComponents: [
    AssetTransactionComponent,
    AssetTransactionUpdateComponent,
    AssetTransactionDeleteDialogComponent,
    AssetTransactionDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsAssetTransactionModule {}

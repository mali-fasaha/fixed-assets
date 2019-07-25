import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FixedAssetsSharedModule } from 'app/shared';
import {
  AssetDisposalComponent,
  AssetDisposalDetailComponent,
  AssetDisposalUpdateComponent,
  AssetDisposalDeletePopupComponent,
  AssetDisposalDeleteDialogComponent,
  assetDisposalRoute,
  assetDisposalPopupRoute
} from './';

const ENTITY_STATES = [...assetDisposalRoute, ...assetDisposalPopupRoute];

@NgModule({
  imports: [FixedAssetsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AssetDisposalComponent,
    AssetDisposalDetailComponent,
    AssetDisposalUpdateComponent,
    AssetDisposalDeleteDialogComponent,
    AssetDisposalDeletePopupComponent
  ],
  entryComponents: [
    AssetDisposalComponent,
    AssetDisposalUpdateComponent,
    AssetDisposalDeleteDialogComponent,
    AssetDisposalDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsAssetDisposalModule {}

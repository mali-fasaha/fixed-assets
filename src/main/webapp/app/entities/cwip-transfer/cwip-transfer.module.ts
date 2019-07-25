import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FixedAssetsSharedModule } from 'app/shared';
import {
  CwipTransferComponent,
  CwipTransferDetailComponent,
  CwipTransferUpdateComponent,
  CwipTransferDeletePopupComponent,
  CwipTransferDeleteDialogComponent,
  cwipTransferRoute,
  cwipTransferPopupRoute
} from './';

const ENTITY_STATES = [...cwipTransferRoute, ...cwipTransferPopupRoute];

@NgModule({
  imports: [FixedAssetsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CwipTransferComponent,
    CwipTransferDetailComponent,
    CwipTransferUpdateComponent,
    CwipTransferDeleteDialogComponent,
    CwipTransferDeletePopupComponent
  ],
  entryComponents: [
    CwipTransferComponent,
    CwipTransferUpdateComponent,
    CwipTransferDeleteDialogComponent,
    CwipTransferDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsCwipTransferModule {}

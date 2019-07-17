import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FixedAssetsSharedModule } from 'app/shared';
import {
  FixedAssetInvoiceComponent,
  FixedAssetInvoiceDetailComponent,
  FixedAssetInvoiceUpdateComponent,
  FixedAssetInvoiceDeletePopupComponent,
  FixedAssetInvoiceDeleteDialogComponent,
  fixedAssetInvoiceRoute,
  fixedAssetInvoicePopupRoute
} from './';

const ENTITY_STATES = [...fixedAssetInvoiceRoute, ...fixedAssetInvoicePopupRoute];

@NgModule({
  imports: [FixedAssetsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FixedAssetInvoiceComponent,
    FixedAssetInvoiceDetailComponent,
    FixedAssetInvoiceUpdateComponent,
    FixedAssetInvoiceDeleteDialogComponent,
    FixedAssetInvoiceDeletePopupComponent
  ],
  entryComponents: [
    FixedAssetInvoiceComponent,
    FixedAssetInvoiceUpdateComponent,
    FixedAssetInvoiceDeleteDialogComponent,
    FixedAssetInvoiceDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsFixedAssetInvoiceModule {}

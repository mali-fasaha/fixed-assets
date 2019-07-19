import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'asset-transaction',
        loadChildren: './asset-transaction/asset-transaction.module#FixedAssetsAssetTransactionModule'
      },
      {
        path: 'scanned-document',
        loadChildren: './scanned-document/scanned-document.module#FixedAssetsScannedDocumentModule'
      },
      {
        path: 'fixed-asset-invoice',
        loadChildren: './fixed-asset-invoice/fixed-asset-invoice.module#FixedAssetsFixedAssetInvoiceModule'
      },
      {
        path: 'fixed-asset-invoice',
        loadChildren: './fixed-asset-invoice/fixed-asset-invoice.module#FixedAssetsFixedAssetInvoiceModule'
      },
      {
        path: 'dealer',
        loadChildren: './dealer/dealer.module#FixedAssetsDealerModule'
      },
      {
        path: 'fixed-asset-invoice',
        loadChildren: './fixed-asset-invoice/fixed-asset-invoice.module#FixedAssetsFixedAssetInvoiceModule'
      },
      {
        path: 'fixed-asset-item',
        loadChildren: './fixed-asset-item/fixed-asset-item.module#FixedAssetsFixedAssetItemModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsEntityModule {}

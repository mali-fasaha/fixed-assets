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
      },
      {
        path: 'service-outlet',
        loadChildren: './service-outlet/service-outlet.module#FixedAssetsServiceOutletModule'
      },
      {
        path: 'fixed-asset-category',
        loadChildren: './fixed-asset-category/fixed-asset-category.module#FixedAssetsFixedAssetCategoryModule'
      },
      {
        path: 'depreciation-regime',
        loadChildren: './depreciation-regime/depreciation-regime.module#FixedAssetsDepreciationRegimeModule'
      },
      {
        path: 'fixed-asset-assessment',
        loadChildren: './fixed-asset-assessment/fixed-asset-assessment.module#FixedAssetsFixedAssetAssessmentModule'
      },
      {
        path: 'fixed-asset-category',
        loadChildren: './fixed-asset-category/fixed-asset-category.module#FixedAssetsFixedAssetCategoryModule'
      },
      {
        path: 'fixed-asset-item',
        loadChildren: './fixed-asset-item/fixed-asset-item.module#FixedAssetsFixedAssetItemModule'
      },
      {
        path: 'asset-depreciation',
        loadChildren: './asset-depreciation/asset-depreciation.module#FixedAssetsAssetDepreciationModule'
      },
      {
        path: 'dealer',
        loadChildren: './dealer/dealer.module#FixedAssetsDealerModule'
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

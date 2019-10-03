import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: 'tables',
    loadChildren: './asset-acquisition-data-table/asset-acquisition-data-table.module#AssetAcquisitionDataTableModule'
  },
  {
    path: 'tables',
    loadChildren: './asset-depreciation-data-table/asset-depreciation-data-table.module#AssetDepreciationDataTableModule'
  },
  {
    path: 'tables',
    loadChildren: './asset-disposal-data-table/asset-disposal-data-table.module#AssetDisposalDataTableModule'
  },
  {
    path: 'tables',
    loadChildren: './asset-item-data-table/asset-item-data-table.module#AssetItemDataTableModule'
  },
  {
    path: 'tables',
    loadChildren: './capital-wip-data-table/capital-wip-data-table.module#CapitalWipDataTableModule'
  },
  {
    path: 'tables',
    loadChildren: './cwip-transfer-data-table/cwip-transfer-data-table.module#CwipTransferDataTableModule'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AppDataTablesRoutingModule {}

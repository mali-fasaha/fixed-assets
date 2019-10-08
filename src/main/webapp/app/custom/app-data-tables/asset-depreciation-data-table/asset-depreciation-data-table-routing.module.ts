import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {
  assetDepreciationDataTablePopupRoute,
  assetDepreciationDataTableRoute
} from 'app/custom/app-data-tables/asset-depreciation-data-table/asset-depreciation-data-table.route';

const routes: Routes = [...assetDepreciationDataTableRoute, ...assetDepreciationDataTablePopupRoute];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AssetDepreciationDataTableRoutingModule {}

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {
  assetAcquisitionDataTablePopupRoute,
  assetAcquisitionDataTableRoute
} from 'app/custom/app-data-tables/asset-acquisition-data-table/asset-acquisition-data-table.route';

const routes: Routes = [...assetAcquisitionDataTableRoute, ...assetAcquisitionDataTablePopupRoute];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AssetAcquisitionDataTableRoutingModule {}

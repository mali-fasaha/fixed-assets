import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {
  assetDisposalDataTablePopupRoute,
  assetDisposalDataTableRoute
} from 'app/custom/app-data-tables/asset-disposal-data-table/asset-disposal-data-table.route';

const routes: Routes = [...assetDisposalDataTableRoute, ...assetDisposalDataTablePopupRoute];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AssetDisposalDataTableRoutingModule {}

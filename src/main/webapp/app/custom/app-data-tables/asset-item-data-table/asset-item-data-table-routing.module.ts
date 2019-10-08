import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {
  assetItemDataTablePopupRoute,
  assetItemDataTableRoute
} from 'app/custom/app-data-tables/asset-item-data-table/asset-item-data-table.route';

const routes: Routes = [...assetItemDataTableRoute, ...assetItemDataTablePopupRoute];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AssetItemDataTableRoutingModule {}

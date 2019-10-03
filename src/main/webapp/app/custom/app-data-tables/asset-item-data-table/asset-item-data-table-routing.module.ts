import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AssetItemDataTableComponent } from 'app/custom/app-data-tables/asset-item-data-table/asset-item-data-table.component';

const routes: Routes = [
  {
    path: 'fixed-asset-item',
    component: AssetItemDataTableComponent,
    data: {
      pageTitle: 'Asset Items List'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AssetItemDataTableRoutingModule {}

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {
  cwipTransferDataTablePopupRoute,
  cwipTransferDataTableRoute
} from 'app/custom/app-data-tables/cwip-transfer-data-table/cwip-transfer-data-table.route';

const routes: Routes = [...cwipTransferDataTableRoute, ...cwipTransferDataTablePopupRoute];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CwipTransferDataTableRoutingModule {}

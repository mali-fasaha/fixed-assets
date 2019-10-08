import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {
  capitalWorkInProgressDataTablesPopupRoute,
  capitalWorkInProgressDataTablesRoute
} from 'app/custom/app-data-tables/capital-wip-data-table/capital-wip-data-table.route';

const routes: Routes = [...capitalWorkInProgressDataTablesRoute, ...capitalWorkInProgressDataTablesPopupRoute];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CapitalWipDataTableRoutingModule {}

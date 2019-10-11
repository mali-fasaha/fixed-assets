import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PREPS_DASHBOARD_ROUTE } from 'app/custom/gha-dashboard/dashboard/dashboard.route';

const routes: Routes = [PREPS_DASHBOARD_ROUTE];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GhaDashboardRoutingModule {}

import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GhaDashboardRoutingModule } from './gha-dashboard-routing.module';
import { FixedAssetsSharedModule } from 'app/shared';
import { DashboardContainerComponent } from './dashboard/dashboard-container.component';
import { UsersDashboardComponent } from './users-dashboard/users-dashboard.component';
import { FixedAssetsAdminModule } from 'app/admin/admin.module';
import { adminState } from 'app/admin';
import { RouterModule } from '@angular/router';
import { MetricDashboardComponent } from './metric-dashboard/metric-dashboard.component';
import { HealthDashboardComponent } from './health-dashboard/health-dashboard.component';
import { FixedAssetsDashboardComponent } from './prepayments-dashboard/prepayments-dashboard.component';
import { DashboardHudContainerModule } from 'app/custom/gha-dashboard/dashboard-hud-container/dashboard-hud-container.module';
import { GhaMaterialModule } from 'app/custom/gha-material.module';
import { AppDataTablesModule } from 'app/custom/app-data-tables/app-data-tables.module';

@NgModule({
  declarations: [
    DashboardContainerComponent,
    UsersDashboardComponent,
    MetricDashboardComponent,
    HealthDashboardComponent,
    FixedAssetsDashboardComponent
  ],
  imports: [
    RouterModule.forChild(adminState),
    FixedAssetsAdminModule,
    FixedAssetsSharedModule,
    CommonModule,
    GhaDashboardRoutingModule,
    AppDataTablesModule,
    GhaMaterialModule,
    DashboardHudContainerModule
  ],
  exports: [DashboardContainerComponent, UsersDashboardComponent],
  entryComponents: [
    DashboardContainerComponent,
    UsersDashboardComponent,
    MetricDashboardComponent,
    HealthDashboardComponent,
    FixedAssetsDashboardComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GhaDashboardModule {}

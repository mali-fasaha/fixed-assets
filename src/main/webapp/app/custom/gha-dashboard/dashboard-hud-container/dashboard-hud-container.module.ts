import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardHudDeckRoutingModule } from './dashboard-hud-deck-routing.module';
import { DashboardHudComponent } from 'app/custom/gha-dashboard/dashboard-hud-container/dashboard-hud/dashboard-hud.component';
import { FixedAssetsSharedModule } from 'app/shared';
import { CountUpModule } from 'countup.js-angular2';
import { DashboardHudNbvComponent } from 'app/custom/gha-dashboard/dashboard-hud-container/dashboard-hud-nbv/dashboard-hud-nbv.component';
import { DashboardHudOrphanedDepreciationComponent } from 'app/custom/gha-dashboard/dashboard-hud-container/dashboard-hud-orphaned-depreciation/dashboard-hud-orphaned-depreciation.component';
import { DashboardHudDepreciationComponent } from 'app/custom/gha-dashboard/dashboard-hud-container/dashboard-hud-depreciation/dashboard-hud-depreciation.component';

@NgModule({
  declarations: [
    DashboardHudComponent,
    DashboardHudNbvComponent,
    DashboardHudDepreciationComponent,
    DashboardHudOrphanedDepreciationComponent
  ],
  exports: [DashboardHudComponent],
  imports: [CommonModule, FixedAssetsSharedModule, DashboardHudDeckRoutingModule, CountUpModule],
  entryComponents: [
    DashboardHudComponent,
    DashboardHudNbvComponent,
    DashboardHudDepreciationComponent,
    DashboardHudOrphanedDepreciationComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DashboardHudContainerModule {}

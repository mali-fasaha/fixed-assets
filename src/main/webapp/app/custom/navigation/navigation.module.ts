import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NavigationRoutingModule } from './navigation-routing.module';
import { AboutMenuComponent } from './about-menu/about-menu.component';
import { ModelMenuComponent } from './model-menu/model-menu.component';
import { ReportMenuComponent } from './report-menu/report-menu.component';
import { ImportMenuComponent } from './import-menu/import-menu.component';
import { ExportMenuComponent } from './export-menu/export-menu.component';
import { FixedAssetsSharedModule } from 'app/shared';
import { MaintenanceMenuComponent } from './maintenance-menu/maintenance-menu.component';

@NgModule({
  declarations: [
    AboutMenuComponent,
    ModelMenuComponent,
    ReportMenuComponent,
    ImportMenuComponent,
    ExportMenuComponent,
    MaintenanceMenuComponent
  ],
  imports: [CommonModule, NavigationRoutingModule, FixedAssetsSharedModule],
  exports: [
    AboutMenuComponent,
    ModelMenuComponent,
    ReportMenuComponent,
    ImportMenuComponent,
    ExportMenuComponent,
    MaintenanceMenuComponent
  ],
  entryComponents: [
    AboutMenuComponent,
    ModelMenuComponent,
    ReportMenuComponent,
    ImportMenuComponent,
    ExportMenuComponent,
    MaintenanceMenuComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NavigationModule {}

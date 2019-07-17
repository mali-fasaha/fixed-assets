import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';

import { NavigationRoutingModule } from './navigation-routing.module';
import { AboutMenuComponent } from './about-menu/about-menu.component';
import { ModelMenuComponent } from './model-menu/model-menu.component';
import { ReportMenuComponent } from './report-menu/report-menu.component';
import { ImportMenuComponent } from './import-menu/import-menu.component';
import { ExportMenuComponent } from './export-menu/export-menu.component';

@NgModule({
  declarations: [AboutMenuComponent, ModelMenuComponent, ReportMenuComponent, ImportMenuComponent, ExportMenuComponent],
  imports: [
    CommonModule,
    NavigationRoutingModule
  ],
  exports: [AboutMenuComponent, ModelMenuComponent, ReportMenuComponent, ImportMenuComponent, ExportMenuComponent],
  entryComponents: [AboutMenuComponent, ModelMenuComponent, ReportMenuComponent, ImportMenuComponent, ExportMenuComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NavigationModule { }

import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SideNavigationRoutingModule } from './side-navigation-routing.module';
import { SideNavComponent } from './side-nav.component';

@NgModule({
  declarations: [SideNavComponent],
  imports: [CommonModule, SideNavigationRoutingModule],
  exports: [SideNavComponent],
  entryComponents: [SideNavComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SideNavigationModule {}

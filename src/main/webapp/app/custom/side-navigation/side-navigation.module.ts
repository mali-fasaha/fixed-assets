import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SideNavigationRoutingModule } from './side-navigation-routing.module';

@NgModule({
  declarations: [],
  imports: [CommonModule, SideNavigationRoutingModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SideNavigationModule {}

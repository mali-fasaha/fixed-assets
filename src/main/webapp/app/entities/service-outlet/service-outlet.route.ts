import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceOutlet } from 'app/shared/model/service-outlet.model';
import { ServiceOutletService } from './service-outlet.service';
import { ServiceOutletComponent } from './service-outlet.component';
import { ServiceOutletDetailComponent } from './service-outlet-detail.component';
import { ServiceOutletUpdateComponent } from './service-outlet-update.component';
import { ServiceOutletDeletePopupComponent } from './service-outlet-delete-dialog.component';
import { IServiceOutlet } from 'app/shared/model/service-outlet.model';

@Injectable({ providedIn: 'root' })
export class ServiceOutletResolve implements Resolve<IServiceOutlet> {
  constructor(private service: ServiceOutletService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceOutlet> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceOutlet>) => response.ok),
        map((serviceOutlet: HttpResponse<ServiceOutlet>) => serviceOutlet.body)
      );
    }
    return of(new ServiceOutlet());
  }
}

export const serviceOutletRoute: Routes = [
  {
    path: '',
    component: ServiceOutletComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ServiceOutlets'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceOutletDetailComponent,
    resolve: {
      serviceOutlet: ServiceOutletResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceOutlets'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceOutletUpdateComponent,
    resolve: {
      serviceOutlet: ServiceOutletResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceOutlets'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceOutletUpdateComponent,
    resolve: {
      serviceOutlet: ServiceOutletResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceOutlets'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceOutletPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceOutletDeletePopupComponent,
    resolve: {
      serviceOutlet: ServiceOutletResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ServiceOutlets'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

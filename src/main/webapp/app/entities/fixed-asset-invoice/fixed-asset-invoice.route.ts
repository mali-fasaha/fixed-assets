import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FixedAssetInvoice } from 'app/shared/model/fixed-asset-invoice.model';
import { FixedAssetInvoiceService } from './fixed-asset-invoice.service';
import { FixedAssetInvoiceComponent } from './fixed-asset-invoice.component';
import { FixedAssetInvoiceDetailComponent } from './fixed-asset-invoice-detail.component';
import { FixedAssetInvoiceUpdateComponent } from './fixed-asset-invoice-update.component';
import { FixedAssetInvoiceDeletePopupComponent } from './fixed-asset-invoice-delete-dialog.component';
import { IFixedAssetInvoice } from 'app/shared/model/fixed-asset-invoice.model';

@Injectable({ providedIn: 'root' })
export class FixedAssetInvoiceResolve implements Resolve<IFixedAssetInvoice> {
  constructor(private service: FixedAssetInvoiceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFixedAssetInvoice> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FixedAssetInvoice>) => response.ok),
        map((fixedAssetInvoice: HttpResponse<FixedAssetInvoice>) => fixedAssetInvoice.body)
      );
    }
    return of(new FixedAssetInvoice());
  }
}

export const fixedAssetInvoiceRoute: Routes = [
  {
    path: '',
    component: FixedAssetInvoiceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'FixedAssetInvoices'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FixedAssetInvoiceDetailComponent,
    resolve: {
      fixedAssetInvoice: FixedAssetInvoiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FixedAssetInvoices'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FixedAssetInvoiceUpdateComponent,
    resolve: {
      fixedAssetInvoice: FixedAssetInvoiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FixedAssetInvoices'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FixedAssetInvoiceUpdateComponent,
    resolve: {
      fixedAssetInvoice: FixedAssetInvoiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FixedAssetInvoices'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fixedAssetInvoicePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FixedAssetInvoiceDeletePopupComponent,
    resolve: {
      fixedAssetInvoice: FixedAssetInvoiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FixedAssetInvoices'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

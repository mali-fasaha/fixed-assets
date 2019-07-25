import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CwipTransfer } from 'app/shared/model/cwip-transfer.model';
import { CwipTransferService } from './cwip-transfer.service';
import { CwipTransferComponent } from './cwip-transfer.component';
import { CwipTransferDetailComponent } from './cwip-transfer-detail.component';
import { CwipTransferUpdateComponent } from './cwip-transfer-update.component';
import { CwipTransferDeletePopupComponent } from './cwip-transfer-delete-dialog.component';
import { ICwipTransfer } from 'app/shared/model/cwip-transfer.model';

@Injectable({ providedIn: 'root' })
export class CwipTransferResolve implements Resolve<ICwipTransfer> {
  constructor(private service: CwipTransferService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICwipTransfer> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CwipTransfer>) => response.ok),
        map((cwipTransfer: HttpResponse<CwipTransfer>) => cwipTransfer.body)
      );
    }
    return of(new CwipTransfer());
  }
}

export const cwipTransferRoute: Routes = [
  {
    path: '',
    component: CwipTransferComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'CwipTransfers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CwipTransferDetailComponent,
    resolve: {
      cwipTransfer: CwipTransferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CwipTransfers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CwipTransferUpdateComponent,
    resolve: {
      cwipTransfer: CwipTransferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CwipTransfers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CwipTransferUpdateComponent,
    resolve: {
      cwipTransfer: CwipTransferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CwipTransfers'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cwipTransferPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CwipTransferDeletePopupComponent,
    resolve: {
      cwipTransfer: CwipTransferResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CwipTransfers'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

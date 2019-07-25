import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TransactionApproval } from 'app/shared/model/transaction-approval.model';
import { TransactionApprovalService } from './transaction-approval.service';
import { TransactionApprovalComponent } from './transaction-approval.component';
import { TransactionApprovalDetailComponent } from './transaction-approval-detail.component';
import { TransactionApprovalUpdateComponent } from './transaction-approval-update.component';
import { TransactionApprovalDeletePopupComponent } from './transaction-approval-delete-dialog.component';
import { ITransactionApproval } from 'app/shared/model/transaction-approval.model';

@Injectable({ providedIn: 'root' })
export class TransactionApprovalResolve implements Resolve<ITransactionApproval> {
  constructor(private service: TransactionApprovalService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransactionApproval> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TransactionApproval>) => response.ok),
        map((transactionApproval: HttpResponse<TransactionApproval>) => transactionApproval.body)
      );
    }
    return of(new TransactionApproval());
  }
}

export const transactionApprovalRoute: Routes = [
  {
    path: '',
    component: TransactionApprovalComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'TransactionApprovals'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransactionApprovalDetailComponent,
    resolve: {
      transactionApproval: TransactionApprovalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransactionApprovals'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransactionApprovalUpdateComponent,
    resolve: {
      transactionApproval: TransactionApprovalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransactionApprovals'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransactionApprovalUpdateComponent,
    resolve: {
      transactionApproval: TransactionApprovalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransactionApprovals'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transactionApprovalPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TransactionApprovalDeletePopupComponent,
    resolve: {
      transactionApproval: TransactionApprovalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TransactionApprovals'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FileUpload } from 'app/shared/model/file-upload.model';
import { FileUploadService } from './file-upload.service';
import { FileUploadComponent } from './file-upload.component';
import { FileUploadDetailComponent } from './file-upload-detail.component';
import { FileUploadUpdateComponent } from './file-upload-update.component';
import { FileUploadDeletePopupComponent } from './file-upload-delete-dialog.component';
import { IFileUpload } from 'app/shared/model/file-upload.model';

@Injectable({ providedIn: 'root' })
export class FileUploadResolve implements Resolve<IFileUpload> {
  constructor(private service: FileUploadService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFileUpload> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FileUpload>) => response.ok),
        map((fileUpload: HttpResponse<FileUpload>) => fileUpload.body)
      );
    }
    return of(new FileUpload());
  }
}

export const fileUploadRoute: Routes = [
  {
    path: '',
    component: FileUploadComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'FileUploads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FileUploadDetailComponent,
    resolve: {
      fileUpload: FileUploadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FileUploads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FileUploadUpdateComponent,
    resolve: {
      fileUpload: FileUploadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FileUploads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FileUploadUpdateComponent,
    resolve: {
      fileUpload: FileUploadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FileUploads'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fileUploadPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FileUploadDeletePopupComponent,
    resolve: {
      fileUpload: FileUploadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FileUploads'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

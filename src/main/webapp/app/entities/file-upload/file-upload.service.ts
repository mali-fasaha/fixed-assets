import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFileUpload } from 'app/shared/model/file-upload.model';

type EntityResponseType = HttpResponse<IFileUpload>;
type EntityArrayResponseType = HttpResponse<IFileUpload[]>;

@Injectable({ providedIn: 'root' })
export class FileUploadService {
  public resourceUrl = SERVER_API_URL + 'api/file-uploads';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/file-uploads';

  constructor(protected http: HttpClient) {}

  create(fileUpload: IFileUpload): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fileUpload);
    return this.http
      .post<IFileUpload>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fileUpload: IFileUpload): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fileUpload);
    return this.http
      .put<IFileUpload>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFileUpload>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFileUpload[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFileUpload[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(fileUpload: IFileUpload): IFileUpload {
    const copy: IFileUpload = Object.assign({}, fileUpload, {
      periodFrom: fileUpload.periodFrom != null && fileUpload.periodFrom.isValid() ? fileUpload.periodFrom.format(DATE_FORMAT) : null,
      periodTo: fileUpload.periodTo != null && fileUpload.periodTo.isValid() ? fileUpload.periodTo.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.periodFrom = res.body.periodFrom != null ? moment(res.body.periodFrom) : null;
      res.body.periodTo = res.body.periodTo != null ? moment(res.body.periodTo) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fileUpload: IFileUpload) => {
        fileUpload.periodFrom = fileUpload.periodFrom != null ? moment(fileUpload.periodFrom) : null;
        fileUpload.periodTo = fileUpload.periodTo != null ? moment(fileUpload.periodTo) : null;
      });
    }
    return res;
  }
}

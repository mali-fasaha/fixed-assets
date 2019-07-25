/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CapitalWorkInProgressService } from 'app/entities/capital-work-in-progress/capital-work-in-progress.service';
import { ICapitalWorkInProgress, CapitalWorkInProgress } from 'app/shared/model/capital-work-in-progress.model';

describe('Service Tests', () => {
  describe('CapitalWorkInProgress Service', () => {
    let injector: TestBed;
    let service: CapitalWorkInProgressService;
    let httpMock: HttpTestingController;
    let elemDefault: ICapitalWorkInProgress;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CapitalWorkInProgressService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CapitalWorkInProgress(0, currentDate, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            transactionMonth: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a CapitalWorkInProgress', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            transactionMonth: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            transactionMonth: currentDate
          },
          returnedFromService
        );
        service
          .create(new CapitalWorkInProgress(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a CapitalWorkInProgress', async () => {
        const returnedFromService = Object.assign(
          {
            transactionMonth: currentDate.format(DATE_FORMAT),
            assetSerialTag: 'BBBBBB',
            serviceOutletCode: 'BBBBBB',
            transactionId: 1,
            transactionDetails: 'BBBBBB',
            transactionAmount: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            transactionMonth: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of CapitalWorkInProgress', async () => {
        const returnedFromService = Object.assign(
          {
            transactionMonth: currentDate.format(DATE_FORMAT),
            assetSerialTag: 'BBBBBB',
            serviceOutletCode: 'BBBBBB',
            transactionId: 1,
            transactionDetails: 'BBBBBB',
            transactionAmount: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            transactionMonth: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CapitalWorkInProgress', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

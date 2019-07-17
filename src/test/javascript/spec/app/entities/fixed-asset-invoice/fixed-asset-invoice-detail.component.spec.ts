/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { FixedAssetInvoiceDetailComponent } from 'app/entities/fixed-asset-invoice/fixed-asset-invoice-detail.component';
import { FixedAssetInvoice } from 'app/shared/model/fixed-asset-invoice.model';

describe('Component Tests', () => {
  describe('FixedAssetInvoice Management Detail Component', () => {
    let comp: FixedAssetInvoiceDetailComponent;
    let fixture: ComponentFixture<FixedAssetInvoiceDetailComponent>;
    const route = ({ data: of({ fixedAssetInvoice: new FixedAssetInvoice(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [FixedAssetInvoiceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FixedAssetInvoiceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FixedAssetInvoiceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fixedAssetInvoice).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

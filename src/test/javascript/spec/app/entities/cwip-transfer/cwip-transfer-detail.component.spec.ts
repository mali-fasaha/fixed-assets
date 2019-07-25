/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { CwipTransferDetailComponent } from 'app/entities/cwip-transfer/cwip-transfer-detail.component';
import { CwipTransfer } from 'app/shared/model/cwip-transfer.model';

describe('Component Tests', () => {
  describe('CwipTransfer Management Detail Component', () => {
    let comp: CwipTransferDetailComponent;
    let fixture: ComponentFixture<CwipTransferDetailComponent>;
    const route = ({ data: of({ cwipTransfer: new CwipTransfer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [CwipTransferDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CwipTransferDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CwipTransferDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cwipTransfer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

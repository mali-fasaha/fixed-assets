/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { AssetDisposalDetailComponent } from 'app/entities/asset-disposal/asset-disposal-detail.component';
import { AssetDisposal } from 'app/shared/model/asset-disposal.model';

describe('Component Tests', () => {
  describe('AssetDisposal Management Detail Component', () => {
    let comp: AssetDisposalDetailComponent;
    let fixture: ComponentFixture<AssetDisposalDetailComponent>;
    const route = ({ data: of({ assetDisposal: new AssetDisposal(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [AssetDisposalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AssetDisposalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssetDisposalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.assetDisposal).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

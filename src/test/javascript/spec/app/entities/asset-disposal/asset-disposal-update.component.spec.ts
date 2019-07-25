/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { AssetDisposalUpdateComponent } from 'app/entities/asset-disposal/asset-disposal-update.component';
import { AssetDisposalService } from 'app/entities/asset-disposal/asset-disposal.service';
import { AssetDisposal } from 'app/shared/model/asset-disposal.model';

describe('Component Tests', () => {
  describe('AssetDisposal Management Update Component', () => {
    let comp: AssetDisposalUpdateComponent;
    let fixture: ComponentFixture<AssetDisposalUpdateComponent>;
    let service: AssetDisposalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [AssetDisposalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AssetDisposalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AssetDisposalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssetDisposalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AssetDisposal(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AssetDisposal();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

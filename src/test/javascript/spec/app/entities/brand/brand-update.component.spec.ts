/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { BrandUpdateComponent } from 'app/entities/brand/brand-update.component';
import { BrandService } from 'app/entities/brand/brand.service';
import { Brand } from 'app/shared/model/brand.model';

describe('Component Tests', () => {
    describe('Brand Management Update Component', () => {
        let comp: BrandUpdateComponent;
        let fixture: ComponentFixture<BrandUpdateComponent>;
        let service: BrandService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [BrandUpdateComponent]
            })
                .overrideTemplate(BrandUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BrandUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BrandService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Brand(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.brand = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Brand();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.brand = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

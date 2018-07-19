/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { ItemDetailComponent } from 'app/entities/item/item-detail.component';
import { Item } from 'app/shared/model/item.model';

describe('Component Tests', () => {
    describe('Item Management Detail Component', () => {
        let comp: ItemDetailComponent;
        let fixture: ComponentFixture<ItemDetailComponent>;
        const route = ({ data: of({ item: new Item(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [ItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.item).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

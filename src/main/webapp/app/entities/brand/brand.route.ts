import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Brand } from 'app/shared/model/brand.model';
import { BrandService } from './brand.service';
import { BrandComponent } from './brand.component';
import { BrandDetailComponent } from './brand-detail.component';
import { BrandUpdateComponent } from './brand-update.component';
import { BrandDeletePopupComponent } from './brand-delete-dialog.component';
import { IBrand } from 'app/shared/model/brand.model';

@Injectable({ providedIn: 'root' })
export class BrandResolve implements Resolve<IBrand> {
    constructor(private service: BrandService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((brand: HttpResponse<Brand>) => brand.body));
        }
        return of(new Brand());
    }
}

export const brandRoute: Routes = [
    {
        path: 'brand',
        component: BrandComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Brands'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'brand/:id/view',
        component: BrandDetailComponent,
        resolve: {
            brand: BrandResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Brands'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'brand/new',
        component: BrandUpdateComponent,
        resolve: {
            brand: BrandResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Brands'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'brand/:id/edit',
        component: BrandUpdateComponent,
        resolve: {
            brand: BrandResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Brands'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const brandPopupRoute: Routes = [
    {
        path: 'brand/:id/delete',
        component: BrandDeletePopupComponent,
        resolve: {
            brand: BrandResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Brands'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

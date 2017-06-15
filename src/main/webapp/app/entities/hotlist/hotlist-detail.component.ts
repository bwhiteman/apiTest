import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Hotlist } from './hotlist.model';
import { HotlistService } from './hotlist.service';

@Component({
    selector: 'jhi-hotlist-detail',
    templateUrl: './hotlist-detail.component.html'
})
export class HotlistDetailComponent implements OnInit, OnDestroy {

    hotlist: Hotlist;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private hotlistService: HotlistService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHotlists();
    }

    load(id) {
        this.hotlistService.find(id).subscribe((hotlist) => {
            this.hotlist = hotlist;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHotlists() {
        this.eventSubscriber = this.eventManager.subscribe(
            'hotlistListModification',
            (response) => this.load(this.hotlist.id)
        );
    }
}
